import React, {Component} from 'react';
import {connect} from 'react-redux';
import {findDOMNode} from 'react-dom';
import {Home} from "app/modules/home/home";
import HeatCycleGraphic from "app/modules/dashboard/heatCycleGraphic";
import {Processdata} from "app/entities/processdata/processdata";


interface IDashboardProps {
  processdata: Processdata;
}

interface IDashboardState {
  sizefactor: number;
}

export class Dashboard extends Component<IDashboardProps, IDashboardState> {

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
    const sizefactorH = (window.innerHeight - 160) / 70;
    const factor = Number((sizefactorW < sizefactorH ? sizefactorW : sizefactorH).toFixed(1));

    this.setState({
      sizefactor: factor < minSizefactor ? minSizefactor : factor
    });
  };

  render() {
    return (
      <div>
        <HeatCycleGraphic width={200} height={200} sizefactor={this.state.sizefactor} fontsize={12}/>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {};
}

export default connect(
  mapStateToProps,
)(Dashboard);

