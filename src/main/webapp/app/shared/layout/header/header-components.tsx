import React from 'react';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from 'app/config/constants';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/logo-jhipster.png" alt="Logo" />
  </div>
);

export const Brand = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="brand-title">Heatpump</span>
    <span className="navbar-version">{appConfig.VERSION}</span>
  </NavbarBrand>
);

export const Home = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>Home</span>
    </NavLink>
  </NavItem>
);

export const ControlPanel = props => (
  <NavItem>
    <NavLink tag={Link} to="/controlpanel" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>ControlPanel</span>
    </NavLink>
  </NavItem>
);

export const DataView = props => (
  <NavItem>
    <NavLink tag={Link} to="/dataview" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>DataView</span>
    </NavLink>
  </NavItem>
);
