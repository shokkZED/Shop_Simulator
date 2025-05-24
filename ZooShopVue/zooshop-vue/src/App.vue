<template>
  <div id="app-container">
    <h1>Vue to Spark Java Shop Simulator</h1>

    <div class="balance-section">
      <h2>Your balance: ${{ userBalance.toFixed(2) }}</h2>
      <button @click="updateBalance" class="btn tertiary-btn small-btn">Reset balance</button>
      <div class="balance-controls">
        <div class="form-group">
          <label for="balance-amount">Change the balance to:</label>
          <input id="balance-amount" v-model.number="balanceChangeAmount" type="number" step="0.01" />
        </div>
        <button @click="addBalance" class="btn primary-btn small-btn">deposit</button>
        <button @click="deductBalance" class="btn secondary-btn small-btn">withdraw</button>
      </div>
    </div>

    <hr />

    <div class="form-section">
      <h2>Add New Product</h2>
      <form @submit.prevent="submitAdd" class="product-form">
        <div class="form-group">
          <label for="product-name">Product Name:</label>
          <input id="product-name" v-model="newProduct.name" type="text" required />
        </div>
        <div class="form-group">
          <label for="product-price">Price:</label>
          <input id="product-price" v-model.number="newProduct.price" type="number" min="0" required />
        </div>
        <button type="submit" class="btn primary-btn">Add Product</button>
      </form>
    </div>

    <hr />

    <div class="form-section">
      <h2>Purchase Product</h2>
      <form @submit.prevent="submitPurchase" class="product-form">
        <div class="form-group">
          <label for="purchase-name">Product Name:</label>
          <input id="purchase-name" v-model="purchaseName" type="text" required />
        </div>
        <button type="submit" class="btn secondary-btn">Purchase Product</button>
      </form>
    </div>

    <hr />

    <div class="action-section">
      <h2>View Products</h2>
      <button @click="getProducts" class="btn tertiary-btn">Get Product List</button>
    </div>

    <div class="response-section">
      <h2>Server Response:</h2>
      <pre class="response-box">{{ response }}</pre>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newProduct: {
        name: '',
        price: null,
      },
      purchaseName: '',
      response: '',
      userBalance: 1000.00, 
      balanceChangeAmount: 100.00,
    };
  },
  methods: {
    async getProducts() {
      try {
        const res = await fetch('http://localhost:4567/products');
        this.response = await res.json();
      } catch (err) {
        this.response = 'GET error: ' + err.message;
      }
    },

    async submitAdd() {
      try {
        const res = await fetch('http://localhost:4567/add', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.newProduct),
        });
        this.response = await res.text();

        this.newProduct.name = '';
        this.newProduct.price = null;
      } catch (err) {
        this.response = 'POST error (add): ' + err.message;
      }
    },

    async submitPurchase() {
      const productName = this.purchaseName;
      let productPrice = 0;

      try {
        const productsRes = await fetch('http://localhost:4567/products');
        const products = await productsRes.json();
        const product = products.find(p => p.name.toLowerCase() === productName.toLowerCase());

        if (product) {
          productPrice = product.price;
        } else {
          this.response = `Product "${productName}" not found.`;
          return;
        }
      } catch (err) {
        this.response = 'Error fetching products for price check: ' + err.message;
        return;
      }

      if (this.userBalance >= productPrice) {
        try {
          const res = await fetch('http://localhost:4567/purchase', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name: this.purchaseName }),
          });
          const serverResponse = await res.text();
          this.response = serverResponse;

          if (res.ok) {
            this.userBalance -= productPrice;
          }

          this.purchaseName = '';
        } catch (err) {
          this.response = 'POST error (purchase): ' + err.message;
        }
      } else {
        this.response = `Not enough funds. Your balance: $${this.userBalance.toFixed(2)}, required: $${productPrice.toFixed(2)}.`;
      }
    },

    async updateBalance() {
        this.userBalance = 1000.00;
        this.response = 'The balance has been reset.';
    },

    addBalance() {
      if (this.balanceChangeAmount > 0) {
        this.userBalance += this.balanceChangeAmount;
        this.response = `Balance replenished by $${this.balanceChangeAmount.toFixed(2)}. New balance: $${this.userBalance.toFixed(2)}.`;
      } else {
        this.response = 'The amount of deposit must be positive.';
      }
    },

    deductBalance() {
      if (this.balanceChangeAmount > 0) {
        if (this.userBalance >= this.balanceChangeAmount) {
          this.userBalance -= this.balanceChangeAmount;
          this.response = `Taken out of the balance $${this.balanceChangeAmount.toFixed(2)}. New Balance: $${this.userBalance.toFixed(2)}.`;
        } else {
          this.response = `Insufficient funds for withdrawal $${this.balanceChangeAmount.toFixed(2)}. Current balance: $${this.userBalance.toFixed(2)}.`;
        }
      } else {
        this.response = 'The amount of the withdrawal must be positive.';
      }
    },
  },
  mounted() {
    this.updateBalance();
  }
};
</script>

<style>

body {
  display: flex;
  justify-content: center; 
  align-items: flex-start; 
  min-height: 100vh; 
  margin: 0; 
  background-color: #e0e0e0; 
  box-sizing: border-box; 
}
</style>

<style scoped>
#app-container {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  width: 65vw;
  margin: 40px auto; 
  padding: 30px;
  border: 1px solid #dcdcdc;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
  background-color: #f8f8f8;
  box-sizing: border-box; 
}

h1 {
  text-align: center;
  color: #333333;
  margin-bottom: 40px;
  font-size: 2.5em;
  letter-spacing: -0.5px;
}

h2 {
  color: #555555;
  margin-top: 30px;
  margin-bottom: 20px;
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 10px;
  font-size: 1.6em;
}

.form-section, .action-section, .response-section, .balance-section {
  background-color: #ffffff;
  padding: 25px;
  border-radius: 8px;
  margin-bottom: 25px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.balance-section h2 {
  text-align: center;
  border-bottom: none;
  margin-top: 0;
  color: #333333;
  font-size: 2em;
}

.balance-controls {
  display: flex;
  align-items: flex-end;
  gap: 10px;
  margin-top: 15px;
  flex-wrap: wrap;
}

.balance-controls .form-group {
  flex-grow: 1;
  margin-bottom: 0;
}

.balance-controls input {
  max-width: 150px;
}

.product-form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 8px;
  font-weight: bold;
  color: #666666;
  font-size: 0.95em;
}

input[type="text"],
input[type="number"] {
  padding: 12px;
  border: 1px solid #cccccc;
  border-radius: 5px;
  font-size: 1em;
  color: #333333;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

input[type="text"]:focus,
input[type="number"]:focus {
  border-color: #888888;
  outline: none;
  box-shadow: 0 0 0 3px rgba(136, 136, 136, 0.2);
}

.btn {
  padding: 12px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1em;
  font-weight: bold;
  color: white;
  transition: background-color 0.3s ease, transform 0.2s ease;
  margin-top: 10px;
}

.btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.small-btn {
  padding: 8px 15px;
  font-size: 0.9em;
  margin-top: 0;
}

.primary-btn {
  background-color: #555555;
}

.primary-btn:hover {
  background-color: #333333;
}

.secondary-btn {
  background-color: #777777;
}

.secondary-btn:hover {
  background-color: #555555;
}

.tertiary-btn {
  background-color: #999999;
}

.tertiary-btn:hover {
  background-color: #777777;
}

hr {
  border: 0;
  height: 1px;
  background-image: linear-gradient(to right, rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.08), rgba(0, 0, 0, 0));
  margin: 40px 0;
}

.response-box {
  background-color: #eeeeee;
  border: 1px solid #dddddd;
  padding: 15px;
  border-radius: 5px;
  white-space: pre-wrap;
  font-family: 'Consolas', 'Courier New', monospace;
  color: #444444;
  font-size: 0.9em;
  max-height: 200px;
  overflow-y: auto;
}
</style>