const { GraphQLInputObjectType, GraphQLString, GraphQLNonNull } = require('graphql');

const CompanyType = require('../types/company');
const CompanyModel = require('../../model/Company');

module.exports = {
	type: CompanyType,
	args: {
		name: {
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
	resolve: async (parent, args, { user }) => {
		if (user) {
			let company = new CompanyModel({
                name: args.name,
                address: args.address,
                zipcode: args.zipcode,
                city: args.city,
                email: args.email,
                phoneNumber: args.phonenumber,
                userId: user.id
            })
            return company.save()
		} else throw Error('you are not authorized')
	}
}

