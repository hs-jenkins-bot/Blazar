import React, {Component, PropTypes} from 'react';
const Link = require('react-router').Link;
import Icon from '../shared/Icon.jsx';
import {bindAll} from 'underscore';
import Loader from '../shared/Loader.jsx';
import ClassNames from 'classnames';

class HostDropdownBreadcrumb extends Component {

  constructor() {
    bindAll(this, 'handleOrgClick', 'handleLinkClick');

    this.state = {
      active: false
    }
  }
  
  handleOrgClick() {
    this.setState({
      active: !this.state.active
    });
  }

  handleLinkClick() {
    this.setState({
      active: false
    });
  }
  
  getContainerClassNames() {
    return ClassNames([
      'crumb',
      'org-nav',
      {'active-page': this.props.activePage}
    ])
  }

  render() {
    const page = this.props.page;
    let HostsWithOrgs;
    
    const orgItems = this.props.hosts.map((host, i) => {

      const orgs = host.orgs.map((org, i) => {
        return (
          <li key={i}>
            <Link to={org.blazarPath} onClick={this.handleLinkClick}>
              {org.name}
            </Link>
          </li>
        );
      });

      return (
        <div key={i} className='org-nav__host'>
          {host.name}
          <ul className='org-nav__host-orgs'>
            {orgs}
          </ul>
        </div>
      );
    });

    if (this.state.active) {
      let loader;
      if (orgItems.length === 0) {
        loader = (
          <Loader align='center' />
        );
      }

      HostsWithOrgs = (
        <div className='org-nav__links'>
          {orgItems}
          {loader}
        </div>
      )
    }

    return (
      <span className={this.getContainerClassNames()}>
        <span className='org-nav__headline' onClick={this.handleOrgClick}>
          <Icon name='caret-down' />
          {page}
        </span>
        {HostsWithOrgs}
      </span>
    )

  }
}

HostDropdownBreadcrumb.propTypes = {
  hosts: PropTypes.array,
  page: PropTypes.string
};


export default HostDropdownBreadcrumb;
