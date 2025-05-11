import { useState } from "react";
import { registerUser } from "../api/user";
import { useNavigate } from "react-router-dom";
import "../index.css";

export default function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      const res = await registerUser(username, password);
      if (res.data === "success") {
        navigate("/login");
      } else {
        setError("Username already taken.");
      }
    } catch {
      setError("Failed to register.");
    }
  };

  return (
    <div className="container">
      <h2>Register</h2>
      <input placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} />
      <input placeholder="Password" type="password" value={password} onChange={e => setPassword(e.target.value)} />
      {error && <div className="error">{error}</div>}
      <button onClick={handleRegister}>Register</button>
      <p>Already have an account? <button onClick={() => navigate("/login")}>Login</button></p>
    </div>
  );
}
