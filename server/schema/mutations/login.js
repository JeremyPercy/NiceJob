const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const _ = require('lodash')
const { GraphQLInputObjectType, GraphQLString, GraphQLNonNull } = require('graphql');
const UserType = require('../types/user');
const User = require('../../model/User');

module.exports = {
	type: GraphQLString,
	args: {
		email: {
			type: new GraphQLNonNull(GraphQLString)
		},
		password: {
			type: new GraphQLNonNull(GraphQLString)
		}
	},
	resolve: async (parent, args, { SECRET }) => {
        const user = await User.findOne({email: args.email});
		if (!user) {
			throw new Error('No user found with that email');
		}
		const valid = await bcrypt.compare(args.password, user.password);
		if (!valid) {
			throw new Error('Incorrect password');
		}
		const token = jwt.sign({
            user: _.pick(user, ['id', 'username', 'email'])
        }, SECRET, {
				expiresIn: '1y'
			}
        )
        return token
	}
};
