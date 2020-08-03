import React, {Component} from 'react';
import { connect } from 'react-redux';

class StatusBar extends Component {

        state = {hide: false}
        msg = '';

    componentDidUpdate() {
        this.state.hide=false;
    }

    resetStatusMsg = () => {
        this.setState({hide:true});
    }


    resetTimeout() {
        clearTimeout(this.statusTimeout);
        this.statusTimeout = setTimeout(this.resetStatusMsg,1000);
    }

    render() {
        if(!this.props.statusMsg || this.state.hide) {
            return (
                <nav className="navbar fadeOut fixed-bottom navbar-light bg-dark text-white-50 justify-content-end">
                    <div className="green led"></div>
                    <div>{this.msg}</div>
                </nav>);
        }
        this.msg = this.props.statusMsg;
        this.resetTimeout();
        return (
            <nav className="navbar fadeIn fixed-bottom navbar-light bg-dark text-white-50 justify-content-end">
                <div className="green led"></div>
                <div>{this.msg}</div>
            </nav>)
    }
}

const mapStateToProps = state => {
    return {
        statusMsg: state.api.statusMsg,
    }
}

export default connect(mapStateToProps, {})(StatusBar);