import React, {Component, PropTypes} from 'react';
import OrgContainer from '../components/org/orgContainer.jsx';
import HeaderContainer from '../components/header/HeaderContainer.jsx';

class Org extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <HeaderContainer  {...this.props} />
        <OrgContainer {...this.props} />
      </div>
    );
  }
}

Org.propTypes = {
  params: PropTypes.object.isRequired
};

export default Org;
