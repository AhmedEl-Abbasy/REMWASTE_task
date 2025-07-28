// File: server.js
const express = require('express');
const cors = require('cors');
const app = express();
const PORT = 5000;

app.use(cors());
app.use(express.json());

let todos = [];
let idCounter = 1;
const mockUser = { email: 'user@test.com', password: 'User123', name: 'Test User' };

app.post('/api/login', (req, res) => {
  const { email, password } = req.body;
  if (email === mockUser.email && password === mockUser.password) {
    res.json({ user: { name: mockUser.name } });
  } else {
    res.status(401).json({ message: 'Invalid credentials' });
  }
});

app.get('/api/todos', (req, res) => {
  res.json(todos);
});


app.get('/api/todos/:id', (req, res) => {
  const id = parseInt(req.params.id); // Convert id from string to number
  const todo = todos.find(t => t.id === id);
  // const todo = todos.find((t) => t.id === parseInt(id));

	res.json(todo );
  // if (todo) {
    // res.json(todos );
  // } else {
    // res.status(404).json({ message: 'Todo not found' });
  // }
});

app.post('/api/todos', (req, res) => {
  const { text } = req.body;
  todos.push({ id: idCounter++, text });
  res.status(201).json({ message: 'Todo created' });
});

app.put('/api/todos/:id', (req, res) => {
  const { id } = req.params;
  const { text } = req.body;
  const todo = todos.find((t) => t.id === parseInt(id));
  if (todo) {
    todo.text = text;
    res.json({ message: 'Todo updated' });
  } else {
    res.status(404).json({ message: 'Todo not found' });
  }
});

app.delete('/api/todos/:id', (req, res) => {
  const { id } = req.params;
  todos = todos.filter((t) => t.id !== parseInt(id));
  res.json({ message: 'Todo deleted' });
});

app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));
