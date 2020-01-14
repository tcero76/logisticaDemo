import axios from 'axios';
import { ACCESS_TOKEN } from '../util/types';

var api = function () {
    return axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL || '/api/',
    headers: {'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)}
});
}

export default api;