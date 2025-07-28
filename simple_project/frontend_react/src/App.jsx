// File: App.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";

function App() {
  const [user, setUser] = useState(null);
  const [todos, setTodos] = useState([]);
  const [text, setText] = useState("");
  const [editId, setEditId] = useState(null);
  const [loginForm, setLoginForm] = useState({ email: "", password: "" });
  const [message, setMessage] = useState("");

  const login = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:5000/api/login", loginForm);
      setUser(res.data.user);
      setMessage("Login successful!");
    } catch (err) {
      console.error(err);
      setMessage("Login failed. Check your credentials.");
    }
  };

  const fetchTodos = async () => {
    const res = await axios.get("http://localhost:5000/api/todos");
    setTodos(res.data);
  };

  const createTodo = async () => {
    await axios.post("http://localhost:5000/api/todos", { text });
    setText("");
    setMessage("New todo added.");
    fetchTodos();
  };

  const editTodo = async () => {
    await axios.put(`http://localhost:5000/api/todos/${editId}`, { text });
    setText("");
    setEditId(null);
    setMessage("Todo updated successfully.");
    fetchTodos();
  };

  const deleteTodo = async (id) => {
    await axios.delete(`http://localhost:5000/api/todos/${id}`);
    setMessage("Todo deleted successfully.");
    fetchTodos();
  };

  useEffect(() => {
    if (user) fetchTodos();
  }, [user]);

  return (
    <div className="p-6 max-w-xl mx-auto">
      {!user ? (
        <form onSubmit={login} className="space-y-4">
          <h2 className="text-xl mb-2">Login</h2>
          {message && <p className="text-status-login">{message}</p>}
          <input
            type="email"
            placeholder="Email"
            className="border p-2 w-full"
            value={loginForm.email}
            onChange={(e) => setLoginForm({ ...loginForm, email: e.target.value })}
            required
          />
          <input
            type="password"
            placeholder="Password"
            className="border p-2 w-full"
            value={loginForm.password}
            onChange={(e) => setLoginForm({ ...loginForm, password: e.target.value })}
            required
          />
          <button type="submit" className="bg-blue-500 text-white p-2 w-full">
            Login
          </button>
        </form>
      ) : (
        <>
          <h2 className="text-xl mb-4">Welcome, {user.name}</h2>
          {message && <p className="text-status-message">{message}</p>}
          <input
            value={text}
            onChange={(e) => setText(e.target.value)}
            placeholder="Enter todo"
			id="Enter-ToDo"
            className="border p-2 w-full mb-2"
          />
          <button
            className="bg-green-500 text-white p-2 mb-4 w-full"
			id="btn-todo"
            onClick={editId ? editTodo : createTodo}
          >
            {editId ? "Update" : "Add"} Todo
          </button>
          <ul>
            {todos.map((todo) => (
              <li
                key={todo.id}
                className="item-line-in-list-mb-2"
              >
                <span className="text-item-line-in-todo">{todo.text}</span>
                <div>
                  <button
                    className="text-edit"
                    onClick={() => {
                      setText(todo.text);
                      setEditId(todo.id);
                    }}
                  >
                    Edit
                  </button>
                  <button
                    className="text-delete"
                    onClick={() => deleteTodo(todo.id)}
                  >
                    Delete
                  </button>
                </div>
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
}

export default App;
