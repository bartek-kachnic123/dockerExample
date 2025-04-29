import React, { useEffect, useState } from 'react';
import Payments from './Payments';

export default function Products() {
    const [products, setProducts] = useState([]);
    const [selectedProducts, setSelectedProducts] = useState([]);

    useEffect(() => {
        fetch('/products')
            .then(res => res.json())
            .then(data => setProducts(data))
            .catch(err => console.error('Błąd podczas pobierania produktów:', err));
    }, []);

    const addProductToCart = (product) => {
        setSelectedProducts((prevState) => [...prevState, product]);
    };

    return (
        <div>
            <h2>Lista produktów</h2>
            <ul>
                {products.map(product => (
                    <li key={product.id}>
                        <strong>{product.name}</strong> - {product.price} PLN
                        <button onClick={() => addProductToCart(product)}>Dodaj do płatności</button>
                    </li>
                ))}
            </ul>

            <Payments selectedProducts={selectedProducts} />
        </div>
    );
}
