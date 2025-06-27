import React, { useState, useEffect } from 'react';
import api from '../services/api';

function ProductForm({ product, onSave, onClose }) {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    category: '',
    price: '',
    imageUrl: '',
    active: true,
  });
  const [message, setMessage] = useState('');
  const [messageType, setMessageType] = useState(''); // 'success' or 'error'

  useEffect(() => {
    if (product) {
      setFormData({
        name: product.name || '',
        description: product.description || '',
        category: product.category || '',
        price: product.price ? product.price.toString() : '',
        imageUrl: product.imageUrl || '',
        active: product.active,
      });
    } else {
      // Limpa o formulário ao criar um novo
      setFormData({
        name: '',
        description: '',
        category: '',
        price: '',
        imageUrl: '',
        active: true,
      });
    }
  }, [product]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setMessageType('');

    try {
      const dataToSave = {
        ...formData,
        price: parseFloat(formData.price), // Converte preço para número
      };

      if (product) {
        // Atualizar produto existente
        await api.put(`/products/${product.id}`, dataToSave);
        setMessage('Produto atualizado com sucesso!');
        setMessageType('success');
      } else {
        // Criar novo produto
        await api.post('/products', dataToSave);
        setMessage('Produto cadastrado com sucesso!');
        setMessageType('success');
        // Limpa o formulário após o cadastro
        setFormData({
          name: '',
          description: '',
          category: '',
          price: '',
          imageUrl: '',
          active: true,
        });
      }
      onSave(); // Chama a função para recarregar a lista
    } catch (error) {
      console.error('Erro ao salvar produto:', error);
      setMessage('Erro ao salvar produto. Verifique os dados e tente novamente.');
      setMessageType('error');
    }
  };

  return (
    <div className="product-form-container">
      <h3 className="subsection-title">{product ? 'Editar Produto' : 'Cadastrar Produto'}</h3>
      {message && (
        <div className={`message ${messageType === 'success' ? 'message-success' : 'message-error'}`}>
          {message}
        </div>
      )}
      <form onSubmit={handleSubmit}>
        <div className="product-form-grid">
            <div className="form-group">
                <label htmlFor="name" className="form-label">Nome</label>
                <input
                type="text"
                id="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
                className="form-input"
                />
            </div>
            <div className="form-group">
                <label htmlFor="category" className="form-label">Categoria</label>
                <input
                type="text"
                id="category"
                name="category"
                value={formData.category}
                onChange={handleChange}
                required
                className="form-input"
                />
            </div>
            <div className="form-group full-width">
                <label htmlFor="description" className="form-label">Descrição</label>
                <textarea
                id="description"
                name="description"
                value={formData.description}
                onChange={handleChange}
                rows="3"
                className="form-textarea"
                ></textarea>
            </div>
            <div className="form-group">
                <label htmlFor="price" className="form-label">Preço</label>
                <input
                type="number"
                id="price"
                name="price"
                value={formData.price}
                onChange={handleChange}
                step="0.01"
                required
                className="form-input"
                />
            </div>
            <div className="form-group">
                <label htmlFor="imageUrl" className="form-label">URL da Imagem</label>
                <input
                type="text"
                id="imageUrl"
                name="imageUrl"
                value={formData.imageUrl}
                onChange={handleChange}
                className="form-input"
                />
            </div>
            <div className="form-group full-width form-checkbox-group">
                <input
                type="checkbox"
                id="active"
                name="active"
                checked={formData.active}
                onChange={handleChange}
                className="form-checkbox"
                />
                <label htmlFor="active" className="form-label">Produto Ativo</label>
            </div>
        </div>
        <div className="button-group">
          <button
            type="button"
            onClick={onClose}
            className="button button-secondary"
          >
            Cancelar
          </button>
          <button
            type="submit"
            className="button button-primary"
          >
            {product ? 'Salvar Alterações' : 'Adicionar Produto'}
          </button>
        </div>
      </form>
    </div>
  );
}

export default ProductForm;