import React, { useState } from 'react';
import { useCart } from '../context/CartContext.jsx';

const Payments = () => {
    const { selectedProducts } = useCart();
    const [loading, setLoading] = useState(false);

    const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || '';
    const apiUrl = `${BACKEND_URL}/carts`;

    function generateUniqueId() {
        return Math.floor(Math.random() * 2_000_000_000);
    }

    const handleSubmit = async () => {
        setLoading(true);

        const productIds = selectedProducts.map(product => product.id);
        const cart = {
            id: generateUniqueId(),
            productIds
        };

        try {
            const response = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(cart)
            });

            if (!response.ok) {
                throw new Error('Wystąpił błąd podczas wysyłania danych');
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <button onClick={handleSubmit} disabled={loading}>
                {loading ? 'Przetwarzanie...' : 'Złóż zamówienie'}
            </button>
        </div>
    );
};

export default Payments;
