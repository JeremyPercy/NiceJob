const { GraphQLID, GraphQLString, GraphQLNonNull } = require('graphql');

const EducationType = require('../types/education');
const EducationModel = require('../../model/Education');

module.exports = {
	type: EducationType,
	args: {
		school: {
			type: GraphQLString
		},
		diploma: {
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
			let education = new EducationModel({
				userId: user.id,
				school: args.school,
				diploma: args.diploma,
				periodeFrom: args.periodefrom,
				periodeTo: args.periodeto
			})
			return education.save();
		} else throw Error('you are not authorized')
	}
}
