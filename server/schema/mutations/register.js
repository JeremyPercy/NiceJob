const bcrypt = require('bcrypt')

const { GraphQLInputObjectType, GraphQLString, GraphQLNonNull } = require('graphql');

const UserType = require('../types/user');
const User = require('../../model/User');

module.exports = {
	type: UserType,
	args: {
		//id: {type: GraphQLID}
		username: {
			type: new GraphQLNonNull(GraphQLString)
		},
		email: {
			type: new GraphQLNonNull(GraphQLString)
		},
		password: {
			type: new GraphQLNonNull(GraphQLString)
		}
	},
	resolve: async (parent, args) => {
		//create object
		let user = new User({
			username: args.username,
			email: args.email,
			password: await bcrypt.hash(args.password, 12)
		})
		//save to db
		return user.save();
	}
}
