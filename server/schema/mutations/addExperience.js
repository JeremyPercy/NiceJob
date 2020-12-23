const { GraphQLID, GraphQLString, GraphQLNonNull } = require('graphql');

const ExperienceType = require('../types/experience');
const ExperienceModel = require('../../model/Experience');

module.exports = {
	type: ExperienceType,
	args: {
		companyname: {
			type: GraphQLString
		},
		experience: {
			type: GraphQLString
		},
		periodefrom: {
			type: GraphQLString
		},
		periodeto: {
			type: GraphQLString
		}
	},
	resolve(parent, args, { user }) {
		if (user) {
			let experience = new ExperienceModel({
				userId: user.id,
				companyName: args.companyname,
				experience: args.experience,
				periodeFrom: args.periodefrom,
				periodeTo: args.periodeto
			})
			return experience.save();
		} else throw Error('you are not authorized')
	}
}
