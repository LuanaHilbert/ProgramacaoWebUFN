import axios from 'axios';

// Instância do Axios pré-configurada para o backend
const api = axios.create({
    baseURL: 'http://localhost:8080/api', // URL base do seu backend Spring Boot
    headers: {
        'Content-Type': 'application/json',
    },
});

export default api;