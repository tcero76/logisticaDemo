import React, { Component } from 'react';
import { connect } from 'react-redux';
import { listarInventario,enviarDespacho } from '../actions/';
import { Field, reduxForm } from 'redux-form';
import NumberFormat from 'react-number-format';

class despacho extends Component {

    state = {inventario: {}}

    componentDidMount() {
        this.props.listarInventario();
    }

    onSubmit = formValues => {
        var inventarioselect = Object.getOwnPropertyNames(formValues);
        var formSubmit = this.props.inventario
            .filter(i => inventarioselect.includes('idinv' + i.idInventario))
            .map(i => {
                return {material: i.material, cantidad: i.cantidad, pos: i.pos, idInventario: i.idInventario, cantidadDespacho: formValues['idinv'+i.idInventario]}
            });
            this.props.enviarDespacho(formSubmit);
            window.location.href = '/despacho';
        }

    renderInput({input , meta: {touched, error}}) {
        var classNam='form-control';
        if(touched) {
            classNam = `form-control ${error ? ' is-invalid' : ' is-valid'}`;
        }
        return (
            <div>
                <NumberFormat style={{width:'85px'}} className={classNam} {...input} autoComplete="off"
                    thousandSeparator="." decimalSeparator="," ></NumberFormat>
                    <div className="invalid-feedback">
                        {error}
                    </div>
                    <div className="valid-feedback">
                        Ok.
                    </div>
            </div>
        );
    }

    renderRow() {
        if(!this.props.inventario)         {
            return <tr></tr>
        }
        return ( this.props.inventario.map( i => {
                    return(
                        <tr key={i.idInventario}>
                            <td>{i.material.idmaterial}</td>
                            <td>{i.material.nombre}</td>
                            <td>{i.cantidad}</td>
                            <td>{i.pos.nombre}</td>
                            <td><Field name={'idinv'+i.idInventario} component={this.renderInput}></Field></td>
                        </tr>
                        );
                })
            );
    }

    renderTable() {
        return (<table className="table mt-3">
                    <thead>
                        <tr>
                            <th>idMaterial</th>
                            <th>Material</th>
                            <th>Cantidad Disponible</th>
                            <th>Ubicación</th>
                            <th>Cantidad Solicitada</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderRow()}
                    </tbody>
                </table>);
    }

    render() {
        return (<div className="container">
                    <h1>Despacho</h1>
                    <form onSubmit={this.props.handleSubmit(this.onSubmit)}>
                        {this.renderTable()}
                        <button className="btn btn-primary">Guardar</button>
                    </form>
                </div>);
    }
}

const mapStateToProps = state => {
    return {inventario: state.api.inventario,
           respuestaOd: state.api.respuestaOd}
}

const validate = (formValues, props) => {
    var errors = {};
    if(!props.inventario) {
        return errors;
    }
    for (const campo in formValues) {
        if (formValues.hasOwnProperty(campo)) {
            const valor = formValues[campo];
            var inv =   props.inventario.find(i => ('idinv'+i.idInventario)===campo);
            if (inv.cantidad<valor) {
                errors[campo]='Sin Stock';
            }
            if(valor<0) {
                errors[campo]='Debe ser positivo';
            }
        }
    }
    return errors;
}

const DespachoForm = reduxForm({
    form:'despachoForm',
    validate
})(despacho);

export default connect(mapStateToProps, { listarInventario, enviarDespacho })(DespachoForm);