const { GraphQLID, GraphQLString, GraphQLNonNull } = require('graphql');

const UserDetailsType = require('../types/userDetails');
const UserDetailsModel = require('../../model/UserDetails');

module.exports = {
	type: UserDetailsType,
	args: {
		id: {
			type: GraphQLID
		},
		name: {
			type: GraphQLString
		},
		lastname: {
			type: GraphQLString
		},
		address: {
			type: GraphQLString
		},
		zipcode: {
			type: GraphQLString
		},
		city: {
			type: GraphQLString
		},
		email: {
			type: GraphQLString
		},
		phonenumber: {
			type: GraphQLString
		}
	},
	resolve(parent, args, { user }) {
		if (user) {
			return (userDetails = UserDetailsModel.findByIdAndUpdate(
				args.id,
				{
					$set: {
						name: args.name,
						lastName: args.lastname,
						address: args.address,
						zipcode: args.zipcode,
						city: args.city,
						email: args.email,
						phoneNumber: args.phonenumber,
						userId: user.id
					}
				},
				{
					new: true
				} //send back updated objectType
			))
		} else throw Error('you are not authorized')
	}
}
