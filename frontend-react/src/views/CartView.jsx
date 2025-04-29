import React, {useEffect, useMemo, useState} from 'react';
import Payments from '../components/Payments.jsx';
import { useCart } from "../context/CartContext.jsx";

function CartView() {
    const { selectedProducts, removeProduct } = useCart();
    const [totalPrice, setTotalPrice] = useState(0);

    const groupedArray = useMemo(() => {
        const groupedProducts = selectedProducts.reduce((acc, product) => {
            if (!acc[product.id]) {
                acc[product.id] = { ...product, quantity: 1 };
            } else {
                acc[product.id].quantity += 1;
            }
            return acc;
        }, {});
        return Object.values(groupedProducts);
    }, [selectedProducts]);

    useEffect(() => {
        const total = groupedArray.reduce((sum, product) => sum + product.price * product.quantity, 0);
        setTotalPrice(total);
    }, [groupedArray]);

    return (
        <div>
            <h2>Koszyk</h2>
            <h2>Podsumowanie płatności</h2>
            {groupedArray.length === 0 ? (
                <p>Nie wybrano żadnych produktów.</p>
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>Nazwa</th>
                        <th>Cena jednostkowa</th>
                        <th>Ilość</th>
                        <th>Suma</th>
                        <th>Akcja</th>
                    </tr>
                    </thead>
                    <tbody>
                    {groupedArray.map((product) => (
                        <tr key={product.id}>
                            <td>{product.name}</td>
                            <td>{product.price} PLN</td>
                            <td>{product.quantity}</td>
                            <td>{product.price * product.quantity} PLN</td>
                            <td>
                                <button onClick={() => removeProduct(product.id)}>Usuń jeden</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
            <p><strong>Całkowita kwota: {totalPrice} PLN</strong></p>
            <Payments />
        </div>
    );
}

export default CartView;
