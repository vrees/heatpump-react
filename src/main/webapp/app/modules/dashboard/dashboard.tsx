import React, {Component} from 'react';
import {connect} from 'react-redux';
import {findDOMNode} from 'react-dom';
import {Home} from "app/modules/home/home";
import Graphic from "app/modules/dashboard/graphic";
import {Processdata} from "app/entities/processdata/processdata";


interface IDashboardProps {
  processdata: Processdata;
}

interface IDashboardState {
  rotation: number;
}

export class Dashboard extends Component<IDashboardProps, IDashboardState> {

  constructor(props) {
    super(props);
    this.state = {
      rotation: 0
    }
    this.tick = this.tick.bind(this);
  }

  componentDidMount() {
    requestAnimationFrame(this.tick);
  }

  tick() {
    const rotation = this.state.rotation + 0.04;
    this.setState({ rotation });
    requestAnimationFrame(this.tick);
  }

  render() {
    return (
      <div>
        <Graphic rotation={this.state.rotation} width={200} height={200} />
        HalloHallo
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

