import React, {Component, PropTypes} from 'react';
import Icon from './Icon.jsx';
import Immutable from 'Immutable';
import ClassNames from 'ClassNames';

class IconStack extends Component {

  getBaseClassNames() {
    return ClassNames([
      "fa-stack-2x",
      this.props.classNames
    ]);
  }
	
	renderIconStackBase() {
		return (
			<Icon type="fa" classNames={this.getBaseClassNames()} name={this.props.iconStackBase} />
		);
	}

	renderIcon(iconName) {
		return (
			<Icon type="fa" classNames="fa-stack-1x" name={iconName} />
		);
	}

	render() {
		return (
			<span className="fa-icon-stack">
				{this.renderIconStackBase()}
				{this.props.iconNames.map(iconName => this.renderIcon(iconName))}
			</span>
		);
	}
}

IconStack.defaultProps = {
	iconStackBase: '',
	iconNames: Immutable.List.of(),
  classNames: Immutable.List.of()
}

IconStack.propTypes = {
	iconStackBase: PropTypes.string,
	iconNames: PropTypes.instanceOf(Immutable.List),
  classNames: PropTypes.instanceOf(Immutable.List)
}

export default IconStack;