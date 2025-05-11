import axios from "axios";
import type { User } from "../interfaces/types";

const API = axios.create({
  baseURL: "http://localhost:8080/api/users",
  withCredentials: false,
});


export const registerUser = (username: string, password: string) =>
  API.post("/register", { username, password });

export const loginUser = (username: string, password: string) =>
  API.post<User>("/login", { username, password });