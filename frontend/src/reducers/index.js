import  {combineReducers} from 'redux';
import { reducer } from 'redux-form';
import api from './api';

export default combineReducers({
    form: reducer,
    api
});