import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import TreeView from '@material-ui/lab/TreeView';
import TreeItem from '@material-ui/lab/TreeItem';
import Typography from '@material-ui/core/Typography';
import House from '@material-ui/icons/House';
import Dehaze from '@material-ui/icons/Dehaze';
import CropSquare from '@material-ui/icons/CropSquare';
import Exposure from '@material-ui/icons/Exposure';
import Label from '@material-ui/icons/Label';
import SupervisorAccountIcon from '@material-ui/icons/SupervisorAccount';
import InfoIcon from '@material-ui/icons/Info';
import ForumIcon from '@material-ui/icons/Forum';
import LocalOfferIcon from '@material-ui/icons/LocalOffer';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import ArrowRightIcon from '@material-ui/icons/ArrowRight';

const useTreeItemStyles = makeStyles(theme => ({
  root: {
    color: theme.palette.text.secondary,
    '&:focus > $content': {
      backgroundColor: `var(--tree-view-bg-color, ${theme.palette.grey[400]})`,
      color: 'var(--tree-view-color)',
    },
  },
  content: {
    color: theme.palette.text.secondary,
    borderTopRightRadius: theme.spacing(2),
    borderBottomRightRadius: theme.spacing(2),
    paddingRight: theme.spacing(1),
    fontWeight: theme.typography.fontWeightMedium,
    '$expanded > &': {
      fontWeight: theme.typography.fontWeightRegular,

    },
  },
  group: {
    // marginLeft: 0,
    '& $content': {
      paddingLeft: theme.spacing(2),
    },
  },
  expanded: {},
  label: {
    fontWeight: 'inherit',
    color: 'inherit',
  },
  labelRoot: {
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0.5, 0),
  },
  labelIcon: {
    marginRight: theme.spacing(1),
    fontSize: '2rem',
  },
  labelText: {
    fontSize: '2rem',
    fontWeight: 'inherit',
    flexGrow: 1,
  },
}));

function Almacen(props) {
  const classes = useTreeItemStyles();
  const { labelText, labelIcon: LabelIcon, labelInfo, color, bgColor, ...other } = props;
  return (
    <TreeItem
      label={
        <div className={classes.labelRoot} onClick={other.onSelect}>
          <House color="inherit" className={classes.labelIcon}/>
          <Typography variant="body2" className={classes.labelText}>
            {labelText}
          </Typography>
          <Typography variant="caption" color="inherit">
            {labelInfo}
          </Typography>
        </div>
      }
      style={{
        '--tree-view-color': color,
        '--tree-view-bg-color': bgColor,
      }}
      classes={{
        root: classes.root,
        content: classes.content,
        expanded: classes.expanded,
        group: classes.group,
        label: classes.label,
      }}
      {...other}
    />
  );
}

function Zona(props) {
  const classes = useTreeItemStyles();
  const { labelText, labelIcon: LabelIcon, labelInfo, color, bgColor, ...other } = props;
  return (
    <TreeItem
      label={
        <div className={classes.labelRoot} onClick={other.onSelect}>
          <CropSquare color="inherit" className={classes.labelIcon} />
          <Typography variant="body2" className={classes.labelText}>
            {labelText}
          </Typography>
          <Typography variant="caption" color="inherit">
            {labelInfo}
          </Typography>
        </div>
      }
      style={{
        '--tree-view-color': color,
        '--tree-view-bg-color': bgColor,
      }}
      {...other}
    />
  );
}

function Nivel(props) {
  const classes = useTreeItemStyles();
  const { labelText, labelIcon: LabelIcon, labelInfo, color, bgColor, ...other } = props;
  return (
    <TreeItem
      label={
        <div className={classes.labelRoot} onClick={other.onSelect}>
          <Dehaze color="inherit" className={classes.labelIcon} />
          <Typography variant="body2" className={classes.labelText}>
            {labelText}
          </Typography>
          <Typography variant="caption" color="inherit">
            {labelInfo}
          </Typography>
        </div>
      }
      style={{
        '--tree-view-color': color,
        '--tree-view-bg-color': bgColor,
      }}
      {...other}
    />
  );
}

function Pos(props) {
  const classes = useTreeItemStyles();
  const { labelText, labelIcon: LabelIcon, labelInfo, color, bgColor, ...other } = props;
  return (
    <TreeItem
      label={
        <div className={classes.labelRoot} onClick={other.onSelect}>
          <Exposure color="inherit" className={classes.labelIcon} />
          <Typography variant="body2" className={classes.labelText}>
            {labelText}
          </Typography>
          <Typography variant="caption" color="inherit">
            {labelInfo}
          </Typography>
        </div>
      }
      style={{
        '--tree-view-color': color,
        '--tree-view-bg-color': bgColor,
      }}
      {...other}
    />
  );
}

Almacen.propTypes = {
  bgColor: PropTypes.string,
  color: PropTypes.string,
  labelIcon: PropTypes.elementType.isRequired,
  labelInfo: PropTypes.string,
  labelText: PropTypes.string.isRequired,
};

class AlmacenTreeView extends Component {

    onSelect(elemento) {
      this.props.onClick(elemento);
    }

  render() {
    if(!this.props.Zonas){
      return null;
    }
    return (
      <TreeView
        style={{
          height: 'auto',
          flexGrow: 1,
          maxWidth: 400,
        }}
        defaultExpanded={['3']}
        defaultCollapseIcon={<ArrowDropDownIcon />}
        defaultExpandIcon={<ArrowRightIcon />}
        defaultEndIcon={<div style={{ width: 24 }} />}
      >
        <Almacen nodeId="idAlmacen" labelIcon={House} onSelect={() => this.onSelect('idAlmacen')}
          labelText={this.props.Almacen}>
          {this.props.Zonas.map(z => {
            return (<Zona key={'zonas'+z.id} nodeId={'zonas'+z.id} labelText={z.nombre} onSelect={() => this.onSelect('zonas'+z.id)}>
                      {z.niveles.map(n => {
                        return (<Nivel key={'niveles'+n.id} nodeId={'niveles'+n.id} labelText={n.nombre} onSelect={() => this.onSelect('niveles'+n.id)}>
                          {n.poses.map(p => {
                            return <Pos key={'poses'+p.id} nodeId={'poses'+p.id} labelText={p.nombre} onSelect={() => this.onSelect('poses'+p.id)}>
                            </Pos>
                          })}
                        </Nivel>);
                      })}
                  </Zona>)
          })}
        </Almacen>
      </TreeView>
  );}
}

export default AlmacenTreeView;