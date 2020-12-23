const { GraphQLInputObjectType, GraphQLString, GraphQLNonNull } = require('graphql');

const UserDetailsType = require('../types/userDetails');
const UserDetailsModel = require('../../model/UserDetails');

module.exports = {
	type: UserDetailsType,
	args: {
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
			let userDetails = new UserDetailsModel({
                name: args.name,
                lastName: args.lastname,
                address: args.address,
                zipcode: args.zipcode,
                city: args.city,
                email: args.email,
                phoneNumber: args.phonenumber,
                userId: user.id
            })
            return userDetails.save()
		} else throw Error('you are not authorized')
	}
}

