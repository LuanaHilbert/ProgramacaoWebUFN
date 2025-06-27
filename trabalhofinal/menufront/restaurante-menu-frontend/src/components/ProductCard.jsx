import React from 'react';
import { useCart } from '../contexts/CartContext';

function ProductCard({ product }) {
    const { addToCart } = useCart();

    const handleImageError = (e) => {
        e.target.onerror = null; // Previne loops infinitos
        e.target.src = `https://placehold.co/400x200/cccccc/333333?text=${encodeURIComponent(product.name)}`;
    };

    return (
        <div className="product-card">
            <img
                src={product.imageUrl || `https://placehold.co/400x200/cccccc/333333?text=Sem%20Imagem`}
                alt={product.name}
                className="product-card-image"
                onError={handleImageError}
            />
            <div className="product-card-content">
                <h4 className="product-card-title">{product.name}</h4>
                <p className="product-card-description">{product.description}</p>
                <div className="product-card-price-category">
                    <span className="product-card-price">R$ {product.price.toFixed(2)}</span>
                    <span className="product-card-category">({product.category})</span>
                </div>
                <button
                    onClick={() => addToCart(product)}
                    className="product-card-button"
                >
                    Adicionar ao Carrinho
                </button>
            </div>
        </div>
    );
}

export default ProductCard;