import React, {useState} from 'react';

const Payments = ({ selectedProducts }) => {
    const [loading, setLoading] = useState(false);

    function generateUniqueId() {
        return  Math.floor(Math.random() * (2_000_000_000));
    }
    const handleSubmit = async () => {
        setLoading(true);

        const productIds = selectedProducts.map(product => product.id);
        const cart = {
            id: generateUniqueId(),
            productIds: productIds
        };

        try {
            const response = await fetch('/carts', {
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
            <h2>Podsumowanie płatności</h2>
            {selectedProducts.length === 0 ? (
                <p>Nie wybrano żadnych produktów.</p>
            ) : (
                <ul>
                    {selectedProducts.map((product, index) => (
                        <li key={index}>
                            <strong>{product.name}</strong> - {product.price} PLN
                        </li>
                    ))}
                </ul>
            )}

            <button onClick={handleSubmit} disabled={loading}>
                {loading ? 'Przetwarzanie...' : 'Złóż zamówienie'}
            </button>
        </div>
    );
};

export default Payments;
