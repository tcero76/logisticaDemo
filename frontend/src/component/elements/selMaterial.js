import React, { Component } from 'react';
import { IntlProvider, FormattedNumber } from 'react-intl';
import IntlData from '../../util/intlData';
import lunr from 'lunr';
import api from '../../util/api';

class SelMaterial extends Component {

    state = {materiales: [], lista:[], cursor: 0, seleccionado:null, touched: false}

    componentDidMount() {
        api().get('/materiales')
        .then(res => {
            this.setState({...this.state, materiales: res.data });
        });
            this.seleccionadoText = document.getElementById("seleccion");
        }

    componentDidUpdate() {
        if(this.state.materiales){
            this.idx = lunr((e) => {
                e.ref('idmaterial')
                e.field('nombre')
                e.field('precio');
                this.state.materiales.forEach(m => e.add(m));
              });
        }
    }

    renderInput() {
    var classNam='form-control';
    if(this.state.touched) {
        classNam = `form-control ${!this.seleccionadoText.firstChild ? ' is-invalid' : ' is-valid'}`;
    }
    return (
            <div className="input-group mb-1 selMaterial__input">
                <div className="input-group-prepend">
                    <span className="input-group-text">
                        <svg className="bi bi-search" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fillRule="evenodd" d="M10.442 10.442a1 1 0 011.415 0l3.85 3.85a1 1 0 01-1.414 1.415l-3.85-3.85a1 1 0 010-1.415z" clipRule="evenodd"/>
                            <path fillRule="evenodd" d="M6.5 12a5.5 5.5 0 100-11 5.5 5.5 0 000 11zM13 6.5a6.5 6.5 0 11-13 0 6.5 6.5 0 0113 0z" clipRule="evenodd"/>
                        </svg>
                    </span>
                </div>
                <div contentEditable="true"
                    id="seleccion"
                    onFocus={e => this.onFocusMaterial()}
                    onBlur={e => this.onBlurMaterial()}
                    onKeyUp={e => this.onKeyUpMaterialb(e)}
                    onKeyDown={e => this.onKeyDownMaterial(e)}
                    className={classNam}>
                </div>
                <div className="invalid-feedback">
                    {this.props.error}
                </div>
                <div className="valid-feedback">
                    {this.props.ok}
                </div>
            </div>
    );
    }

    renderList() {
        if(!this.state.lista || this.state.lista.length ===0) {
            return null;
        }
        return <div className="selMaterial__lista">
            {this.state.lista.map((m,i) => {
                    var material = this.state.materiales
                                    .find(mat => mat.idmaterial === parseInt(m.ref));
                    if(!material){
                        return null;
                    }
                    var clase = 'list-group-item list-group-item-action';
                    return (
                        <div key={m.ref}
                            onMouseDown={e => this.onClickItem(m.ref)}
                            className="list-group selMaterial__lista__item">
                                <a href="#"
                                    className={i===this.state.cursor?clase.concat(' active'):clase}>
                                    <div className="d-flex w-100 justify-content-between">
                                    <h5 className="mb-1">{material.nombre}</h5>
                                    <small>
                                        <FormattedNumber
                                        value={parseInt(material.precio)}
                                        format="CLP"
                                        >
                                        </FormattedNumber>
                                    </small>
                                    </div>
                                    <p className="mb-1">Donec id elit non mi porta gravida at eget metus. Maecenas sed diam eget risus varius blandit.</p>
                                </a>
                            </div>
                    );
                })}
            </div>
    }

    onKeyUpMaterialb(e) {
        if(!this.state.seleccionado){
            this.setState({lista:this.idx.search(e.target.innerText+'*')});
        }
    }

    onKeyDownMaterial(e) {
        if(e.key==='Enter'){
            e.preventDefault();
            if(this.state.lista.length>0) {
                var seleccion = this.state.lista[this.state.cursor].ref;
                this.setState({seleccionado: seleccion});
                this.insertBadge(seleccion);
            }
        }
        if(e.key==='Tab'){
            if(this.state.lista.length>0) {
                var seleccion = this.state.lista[this.state.cursor].ref;
                this.setState({seleccionado: seleccion});
                this.insertBadge(seleccion);
            }
        }
        if(this.state.lista.length-1>this.state.cursor && e.key==='ArrowDown'){
            this.setState({cursor: this.state.cursor+1});
        }
        if(this.state.cursor>0 && e.key==='ArrowUp'){
            this.setState({cursor: this.state.cursor-1});
        }
        if(this.state.seleccionado && e.key!=='Enter' && e.key!=='Tab' &&
            e.key!=='ArrowUp' && e.key!=='ArrowDown') {
            this.state.cursor=0;
            while(this.seleccionadoText.firstChild) {
                this.seleccionadoText.removeChild(this.seleccionadoText.lastChild);
            }
            this.setState({seleccionado: null});
        }
    }

    onClickItem(key) {
        this.insertBadge(key);
    }

    insertBadge(key) {
        while(this.seleccionadoText.firstChild) {
            this.seleccionadoText.removeChild(this.seleccionadoText.lastChild)
        }
        var badge = document.createElement("span")
        badge.setAttribute("contenteditable", false);
        badge.classList.add("badge", "badge-info");
        var material = this.state.materiales
                        .find(l => l.idmaterial == key);
        badge.innerText = material?material.nombre:null;
        this.seleccionadoText.appendChild(badge);
        this.setState({lista: [], seleccionado: key});
        this.props.value(material);
    }

    onFocusMaterial(e) {
        while (this.seleccionadoText.firstChild) {
            this.seleccionadoText.removeChild(this.seleccionadoText.lastChild);
        }
        this.setState({lista: this.idx.search('*'), seleccionado: null});
        this.props.value({idmaterial: null});
    }

    onBlurMaterial() {
        this.setState({ lista: [], touched: true});
        if(!this.state.seleccionado) {
            while(this.seleccionadoText.firstChild) {
                this.seleccionadoText.removeChild(this.seleccionadoText.lastChild)
            }
        }
    }

    render() {
        return (
            <IntlProvider locale="es" {...IntlData}>
                    <div className="selMaterial">
                        <label htmlFor="seleccion">{this.props.label}</label>
                        {this.renderInput()}
                    </div>
                    {this.renderList()}
            </IntlProvider>
            );
    }

    resetMaterial = () => {
        this.setState({ ...this.state, seleccionado: null, touched: false});
        while(this.seleccionadoText.firstChild) {
            this.seleccionadoText.removeChild(this.seleccionadoText.lastChild)
        }
    }
}

export default SelMaterial;