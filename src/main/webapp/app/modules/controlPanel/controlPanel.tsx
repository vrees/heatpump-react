import React, {Component} from 'react';
import {connect} from 'react-redux';
import {findDOMNode} from 'react-dom';
import {Home} from "app/modules/home/home";
import HeatCycleGraphic from "app/modules/controlPanel/heatCycleGraphic";
import {Processdata} from "app/entities/processdata/processdata";


interface IControlPanelProps {
  processdata: Processdata;
}

interface IControlPanelState {
  sizefactor: number;
}

export class ControlPanel extends Component<IControlPanelProps, IControlPanelState> {

  constructor(props) {
    super(props);

    this.state = {
      sizefactor: 8
    }

    window.addEventListener("resize", this.update);
  }

  componentDidMount() {
    this.update();
  }

  update = () => {
    const minSizefactor = 3;
    const sizefactorW = (window.innerWidth - 100) / 110;
    const sizefactorH = (window.innerHeight - 182) / 70;
    const factor = Number((sizefactorW < sizefactorH ? sizefactorW : sizefactorH).toFixed(1));

    this.setState({
      sizefactor: factor < minSizefactor ? minSizefactor : factor
    });
  };

  render() {
    return (
      <div>
        <HeatCycleGraphic sizefactor={this.state.sizefactor} fontsize={12}/>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {};
}

export default connect(
  mapStateToProps,
)(ControlPanel);

