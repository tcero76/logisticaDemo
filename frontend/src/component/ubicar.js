import React from 'react';
import { connect } from 'react-redux';
import { currentNav, listarAlmacen, listarOrec, enviarUbicacion } from '../actions/';
import { Field, reduxForm } from 'redux-form';

class ubicar extends React.Component {

    state = {
        nivelAct: false, nivelOpt: [],
        posAct: false, posOpt: [],
        ubicacion: false, idoritem: null
    };


    componentDidMount() {
        this.props.currentNav(1);
        this.props.listarAlmacen();
        this.props.listarOrec();
    }

    onAdd =(idoritem) => {
        this.setState({...this.state, idoritem: idoritem});
    }

    onDelete = () => {
        // this.setState({...this.state})
    }

    onSubmitUbicacion = () => {
        var pos = this.state.posOpt.find(pos => pos.nombre===this.props.ubicarForm.values.pos);
        this.props.enviarUbicacion({idoritem: this.state.idoritem,
                                    idpos: parseInt(pos.id)
                                    });
    }

    renderConfirm() {
        if(!this.state.idoritem) {
            return null
        }
        var oritem = this.props.tablaOritemPend
                    .find(ori => ori.idoritem === this.state.idoritem);
        if(!oritem) {
            return null;
        }
        return (
                <div className="modal fade" id="idConfirm" tabIndex="-1"
                    role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">Confirmación</h5>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        ¿Está seguro de asignar esta ubicación a Or {oritem.idorec} Posición {oritem.pos}?
                    </div>
                    <div className="modal-footer">
                        <button
                            type="button"
                            className="btn btn-secondary"
                            data-dismiss="modal">
                                Anular
                        </button>
                        <button type="button"
                                onClick={this.onSubmitUbicacion}
                                data-dismiss="modal"
                                className="btn btn-primary">
                                    Confirmar
                        </button>
                    </div>
                    </div>
                </div>
                </div>
        )
    }

    buttonPlus(oritem) {
        if(!this.state.ubicacion) {
            return (
                <button className="btn btn-primary" disabled>
                    <i className="fas fa-plus"></i>
                </button>
            )
        }
        return (<button style={{cursor:'pointer'}}
                    className="btn btn-primary"
                    data-toggle="modal"
                    data-target="#idConfirm"
                    onClick={() => this.onAdd(oritem.idoritem)}>
                    <i className="fas fa-plus"></i>
                </button>);
    }

    buttonMinus(oritem) {
        if(!this.state.ubicacion){
            return(
                <button className="btn btn-primary" disabled>
                    <i className="fas fa-minus"></i>
                </button>
            )
        }
        return (
            <button className="btn btn-primary"
                onClick={this.onDelete}
                style={{cursor:'pointer'}}>
                <i className="fas fa-minus"></i>
            </button>
        )
    }

    renderFilas() {
        if(!this.props.tablaOritemPend)
            return null;
        return this.props.tablaOritemPend.map(oritem => {
                return (
                        <tr key={oritem.idoritem}>
                            <td style={{textAlign: 'center', width:'20px'}}>{oritem.pos}</td>
                            <td style={{textAlign: 'center', width:'20px'}}>{oritem.idorec}</td>
                            <td style={{textAlign: 'center'}}>{oritem.material.nombre}</td>
                            <td style={{textAlign: 'center', width:'20px'}}>{oritem.cantidad}</td>
                            <td style={{textAlign: 'center', width:'20px'}}>
                                {this.buttonPlus(oritem)}
                            </td>
                        </tr>
                );
            });
    }

    renderTabla() {
        return (
            <div className="table-responsive">
                <table className="table">
                    <thead>
                        <tr>
                            <th style={{textAlign: 'center'}}>POS</th>
                            <th style={{textAlign: 'center'}}>OR</th>
                            <th style={{textAlign: 'center'}}>Material</th>
                            <th style={{textAlign: 'center'}}>Cantidad</th>
                            <th style={{width: '120px'}}></th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderFilas()}
                    </tbody>
                </table>
            </div>
        );
    }

    renderItems(ubicaciones) {
        if (!ubicaciones) {
            return null;
        }
        return ubicaciones.map((item => {
            return (
                <option key={item.id}>{item.nombre}</option>
            )
        }))
    }

    renderZona = ({ label, input, meta: { touched, errors }, active, categorias }) => {
        var classNam = 'form-control';
        return (
            <div>
                <label>{label}</label>
                <select className={classNam} {...input} disabled={!active}>
                    <option key={0}>Seleccionar</option>
                    {this.renderItems(categorias)}
                </select>
            </div>
        )
    }

    onChangePos = (event, newValue, previousValue,name) => {
        var idPos = this.state.posOpt.find(pos => pos.nombre===newValue);
        if(newValue==='Seleccionar' || typeof newValue==='undefined') {
            this.setState({...this.state,ubicacion:false});
        } else {
            this.setState({...this.state,ubicacion:true, idPos: idPos.id});
        }
    }

    onChangeNivel = (e, newValue, previousValue, name) => {
        var [nivel] = this.state.nivelOpt.filter(nivel => nivel.nombre === newValue);
        if (newValue !== '' && newValue !== 'Seleccionar') {
            this.setState({ ...this.state, posAct: true, posOpt: nivel.poses });
        } else {
            this.setState({ ...this.state, posAct: false, posOpt: [] });
            this.props.change('pos',null)
        }
    }

    onChangeZona = (event, newValue, previousValue, name) => {
        var [zona] = this.props.ubicaciones.filter(zona => zona.nombre === newValue);
        if (newValue !== '' && newValue !== 'Seleccionar') {
            this.setState({ ...this.state, nivelAct: true, nivelOpt: zona.niveles });
        } else {
            this.setState({ ...this.state, nivelAct: false, nivelOpt: [] , posAct: false, posOpt: []});
            this.props.change('nivel',null);
            this.props.change('pos',null);
        }
    }

    render() {
        return (
            <div className="container">
                <div className="card">
                    <div className="card-body">
                        <h5 className="card-title">Ubicación</h5>
                        <form className="form-row">
                            <div className="row">
                                <div className="col-sm">
                                    <Field name="zona" categorias={this.props.ubicaciones}
                                        active={true} label="Zona"
                                        component={this.renderZona}
                                        onChange={this.onChangeZona} ></Field>
                                </div>
                                <div className="col-sm">
                                    <Field name="nivel"
                                        categorias={this.state.nivelOpt}
                                        active={this.state.nivelAct} label="Nivel"
                                        component={this.renderZona}
                                        onChange={this.onChangeNivel}></Field>
                                </div>
                                <div className="col-sm">
                                    <Field name="pos"
                                        categorias={this.state.posOpt}
                                        active={this.state.posAct} label="Pos"
                                        component={this.renderZona}
                                        onChange={this.onChangePos} >
                                    </Field>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                {this.renderTabla()}
                {this.renderConfirm()}  
            </div>
        );
    }
}

const mapStateToProps = state => {
    var resp = {};
    if (state.api.usuario) {
        resp = { usuario: state.api.usuario };
    }
    if (state.api.ubicaciones) {
        resp = { ...resp, ubicaciones: state.api.ubicaciones }
    }
    if(state.api.tablaOritemPend) {
        resp = { ...resp, tablaOritemPend: Object.values(state.api.tablaOritemPend)}
    }
    resp = { ...resp, ubicarForm: state.form.ubicarForm}
    return resp;
}

const validate = (formValues, state) => {
    var errors = {};
    return errors;
}

const form = reduxForm({
    form: 'ubicarForm',
    validate
})(ubicar)

export default connect(mapStateToProps, { currentNav, listarAlmacen, listarOrec, enviarUbicacion })(form);