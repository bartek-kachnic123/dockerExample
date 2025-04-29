import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Products from './components/Products';
import CartView from './views/CartView.jsx';
import { CartProvider } from './context/CartProvider.jsx';
import './App.css';

function App() {
    return (
        <CartProvider>
            <Router>
                <nav>
                    <Link to="/">Produkty</Link> | <Link to="/cart">Koszyk</Link>
                </nav>
                <Routes>
                    <Route path="/" element={<Products />} />
                    <Route path="/cart" element={<CartView />} />
                </Routes>
            </Router>
        </CartProvider>
    );
}

export default App;
