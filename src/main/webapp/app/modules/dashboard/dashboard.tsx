import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Home} from "app/modules/home/home";

export class Dashboard extends Component {
  render() {
    return (
      <div>

        Hallo


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

