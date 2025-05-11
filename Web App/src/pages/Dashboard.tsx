import { useEffect, useState } from "react";
import { placeOrder, creditFunds, cancelOrder, getBalance, getOrders, getUserOrders, getUserHistory } from "../api/trade";
import "../index.css";


export default function Dashboard() {
  const user = JSON.parse(sessionStorage.getItem("user") || "{}");

  const [price, setPrice] = useState<string>("");
  const [quantity, setQuantity] = useState<number>(0);
  const [side, setSide] = useState("BUY");
  const [funds, setFunds] = useState<string>("");
  const [orderId, setOrderId] = useState("");
  const [balance, setBalance] = useState<string>("");
  const [orders, setOrders] = useState<any[]>([]);
  const [error, setError] = useState<string>("");
  const [userOrders, setUserOrders] = useState<any[]>([]);
  const [history, setHistory] = useState<string[]>([]);
  const [quantityString, setQuantityString] = useState<string>("");

  const fetchUserOrders = async () => {
    try {
      const res = await getUserOrders(user.userId);
      setUserOrders(res.data);
    } catch (err) {
      console.error("Failed to fetch user orders:", err);
    }
  };

  const fetchBalance = async () => {
    try {
      const res = await getBalance(user.userId);
      setBalance(res.data);
    } catch (err) {
      console.error("Failed to fetch balance:", err);
    }
  };

  const fetchOrders = async () => {
    try {
      const res = await getOrders();
      setOrders(res.data);
    } catch (err) {
      console.error("Failed to fetch orders:", err);
    }
  };

  const fetchHistory = async () => {
    try {
      const res = await getUserHistory(user.userId);
      setHistory(res.data);
    } catch (err) {
      console.error("Failed to fetch history:", err)
    }
  };

  useEffect(() => {
    fetchBalance();
  }, []);

  const isValidPositiveNumber = (value: string) =>
    !!value && !isNaN(Number(value)) && Number(value) > 0;
  const isDouble = (value: string) =>
     /^\d+\.?\d+$/.test(value);
  const isInteger = (value: string) =>
     /\d+/.test(value);
      

  const handlePlaceOrder = async () => {
    if (!isValidPositiveNumber(price) || !isValidPositiveNumber(quantityString)) {
      setError("Price and quantity must be greater than zero.");
      return;
    }
    if (!isDouble(price)) {
      setError("Price must be a number.");
      return;
    }
    if (!isInteger(quantityString)) {
      setError("Quantity must be an integer.");
      return;
    }
    setQuantity(parseInt(quantityString));
    console.log(quantity);
    setError("");
    try {
      await placeOrder({
        userId: user.userId,
        side: side.toLowerCase() as "buy" | "sell",
        price,
        quantity,
      });
      fetchBalance();
      setOrders([])
      //fetchOrders()
      setUserOrders([])
      //fetchUserOrders()
      setHistory([])
    } catch (err) {
      setError("Failed to place order.");
    }
  };

  const handleCredit = async () => {
    if (!funds || !isValidPositiveNumber(funds)) {
      setError("Funds must be greater than zero.");
      return;
    }
    setError("");
    try {
      await creditFunds({ userId: user.userId, amount: funds });
      fetchBalance();
      setOrders([])
      //fetchOrders()
      setUserOrders([])
      //fetchUserOrders()
      setHistory([])
    } catch (err) {
      setError("Failed to credit funds.");
    }
  };

  const handleCancelOrder = async () => {
    setError("");
    try {
      await cancelOrder({ orderId, userId: user.userId });
      fetchBalance();
      setOrders([])
      //fetchOrders()
      setUserOrders([])
      //fetchUserOrders()
      setHistory([])
    } catch (err: any) {
      const message = err?.response?.data || "Cancellation failed.";
      setError(message);
    }
  };

  return (
    <div className="container">
      <h2>Dashboard</h2>
      <p><strong>User:</strong> {user.username} ({user.userId})</p>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <h3>Place Order</h3>
      <select value={side} onChange={e => setSide(e.target.value)}>
        <option value="BUY">BUY</option>
        <option value="SELL">SELL</option>
      </select>
      <input
        placeholder="Price"
        type="text"
        step="any"
        value={price ?? ""}
        onChange={e => setPrice(e.target.value)}
      />
      <input
        placeholder="Quantity"
        type="number"
        step="any"
        value={quantityString ?? ""}
        onChange={e => setQuantityString((e.target.value))}
      />
      <button onClick={handlePlaceOrder}>Place Order</button>

      <h3>Credit Funds</h3>
      <input
        placeholder="Amount"
        type="text"
        step="any"
        value={funds ?? ""}
        onChange={e => setFunds(e.target.value)}
      />
      <button onClick={handleCredit}>Credit</button>

      <h3>Cancel Order</h3>
      <input
        placeholder="Order ID"
        value={orderId}
        onChange={e => setOrderId(e.target.value)}
      />
      <button onClick={handleCancelOrder}>Cancel</button>

      <h3>Balance</h3>
      <div>{balance}</div>

      <h3>Order Book</h3>
      <button onClick={fetchOrders}>Show Orders</button>
      <ul className="order-list">
        {orders.map((order) => (
          <li key={order.orderId} style={{ border: "1px solid #ccc", padding: "8px", marginBottom: "6px", borderRadius: "4px" }}>
          <div><strong>Order ID:</strong> {order.orderId}</div>
          <div><strong>User:</strong> {order.userId}</div>
          <div><strong>Side:</strong> {order.side} <strong>Price:</strong> {order.price} <strong>Quantity:</strong> {order.quantity} </div>
          </li>
        ))}
      </ul>
      <h3>My Orders</h3>
    <button onClick={fetchUserOrders}>Show My Orders</button>
    <ul className="order-list">
      {userOrders.map((order) => (
        <li key={order.orderId} style={{ border: "1px solid #ccc", padding: "8px", marginBottom: "6px", borderRadius: "4px" }}>
          <div><strong>Order ID:</strong> {order.orderId}</div>
          <div><strong>Side:</strong> {order.side} <strong>Price:</strong> {order.price} <strong>Quantity:</strong> {order.quantity}</div>
        </li>
      ))}
    </ul>
    <h3>Activity History</h3>
    <button onClick={fetchHistory}>Show History</button>
      <ul style={{ whiteSpace: "pre-wrap" }}>
        {history.map((entry, index) => (
          <li key={index} dangerouslySetInnerHTML={{ __html: entry }}></li>
        ))}
      </ul>
    </div>
  );
}

