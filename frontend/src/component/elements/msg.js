import React, { Component } from 'react';

class Msg extends Component {

    state = { Hide: false }

    resetStatetusMsg = () => {
        this.setState({ Hide: true });
    }

    resetTimeout() {
        clearTimeout(this.statusTimeout);
        this.statusTimeout = setTimeout(this.resetStatetusMsg,2500);
    }


    render() {
        if(!this.props.msg || this.props.Hide || this.state.Hide) {
            this.state.Hide = false;
            return null;
        }
        var clase = '';
        var msg = '';
        switch (this.props.status.toString()) {
            case '401':
                clase = 'alert alert-danger';
                msg = this.props.msg;
                break;
            case '500':
                clase = 'alert alert-danger';
                msg = this.props.msg;
                break;
            case '400':
                clase = 'alert alert-danger';
                msg = this.props.msg;
                break;
            case '201':
                clase = 'alert alert-success';
                msg = `Registrado con id: ${this.props.msg}`;
                break;
            case '404':
                clase = 'alert alert-danger';
                msg = this.props.msg;
                break;
            default:
                clase = 'alert';
        }
        this.resetTimeout();
        return (<div className={clase}>
                    <p>{msg}</p>
                </div>)
    }
}

const mapStateToProps = state => {
    return { 
            msg: state.api.msg, 
            status: state.api.status, 
        }
}

export default Msg;