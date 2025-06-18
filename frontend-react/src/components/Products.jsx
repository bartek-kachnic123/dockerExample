import React, { useEffect, useState } from 'react';
import { useCart } from '../context/CartContext';


export default function Products() {
    const [products, setProducts] = useState([]);
    const { addProduct } = useCart();

    const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || '';
    const apiUrl = `${BACKEND_URL}/products`;
    
    useEffect(() => {
        fetch(apiUrl, { credentials: 'include' })
            .then(res => res.json())
            .then(data => setProducts(data))
            .catch(err => console.error('Błąd podczas pobierania produktów:', err));
    }, []);

    return (
        <div>
            <h2>Lista produktów ze sklepu</h2>
            <ul>
                {products.map(product => (
                    <li key={product.id}>
                        <strong>{product.name}</strong> - {product.price} PLN
                        <button onClick={() => addProduct(product)}>Dodaj do koszyka</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
