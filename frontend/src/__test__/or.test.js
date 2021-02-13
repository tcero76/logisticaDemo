import React from 'react';
import moxios from 'moxios';
import { mount } from 'enzyme';

import Root from '../root';
import App from '../App';
import { wrap } from 'lodash';

let wrapper;

beforeEach(() => {
    moxios.install();
    moxios.stubRequest('/api/usuarios/current', {
        status: 200,
        response: {
            idUsuario: 1,
            nombre: 'llastra',
            isAuthenticated: true,
            almacen: 'Santiago'
        }
    });
    moxios.stubRequest('/api/materiales',{
        status: 200,
        response: [{"idmaterial":1,"nombre":"tijeras","precio":5400.0},{"idmaterial":2,"nombre":"papel","precio":546.0},{"idmaterial":3,"nombre":"goma","precio":340.0},{"idmaterial":4,"nombre":"cuaderno","precio":5640.0},{"idmaterial":5,"nombre":"estuche","precio":2560.0},{"idmaterial":6,"nombre":"lapices","precio":1430.0},{"idmaterial":7,"nombre":"cuadernillo","precio":25.0}]
    });
    moxios.stubRequest('/api/ors',{
        status:200,
        response: 100,
    })
    wrapper = mount(
        <Root>
            <App/>
        </Root>,
        { attachTo: document.body }
    );
});

afterEach(() => {
    moxios.uninstall();
    wrapper.unmount();
});

describe('realizar ingresos de ors', () => {

beforeEach(() => {
    wrapper.update();
    wrapper.find('a.nav-link[href="/or"]').at(0).simulate('click', { button: 0 });
})

    it('puedo pinchar selMateriales y aparece una lista', done => {

        moxios.wait(function() {
            expect(wrapper.find('button').length).toEqual(2);
            done();
            moxios.wait(function() {
                wrapper.find('#seleccion').simulate('focus'); 
                wrapper.update();
                expect(wrapper.find('.list-group-item').length).toEqual(7);
                expect(wrapper.find('div.selMaterial__lista').length).toEqual(1);
                done();
            });
        });
    });
    
    it('puedo ingresar una cantidad y queda registrada', () => {
        wrapper.find('input#cantidad').simulate('change', {
            target: {
                value: '15,6',
                focus: () => {}
            },
        });
        wrapper.update();
        expect(wrapper.find('input#cantidad').length).toEqual(1);
    });
    
    it('puedo ingresar una guia de despacho y queda registrada', () => {
        wrapper.find('input#guiadespacho').simulate('change', {
            target: {
                value: 'asdf'
            },
        });
        wrapper.update();
        expect(wrapper.find('input#guiadespacho').length).toEqual(1);
        expect(wrapper.find('input#guiadespacho').prop('value')).toEqual('asdf');
    });

    it('verificar si aparece la tabla y botón', done => {

        wrapper.find('#seleccion').simulate('focus'); 
        wrapper.update();
        wrapper.find('div.selMaterial__lista__item').at(2).simulate('mousedown');
        wrapper.update();
        wrapper.find('input#cantidad').simulate('change', {
            target: {
                value: '15,6',
                focus: () => {},
            },
        });
        wrapper.find('input#guiadespacho').simulate('change', {
            target: {
                value: 'asdf',
            },
        });
        wrapper.update();
        wrapper.find('button.btn').simulate('click');
        wrapper.update();
        expect(wrapper.find('table').length).toEqual(1);
        expect(wrapper.find('button').length).toEqual(3);
        wrapper.find('button').at(2).simulate('click');
        moxios.wait(()=>{
            wrapper.update();
            expect(wrapper.find('table').length).toEqual(0);
            expect(wrapper.find('button').length).toEqual(2);
            done();
        })
    });

});
