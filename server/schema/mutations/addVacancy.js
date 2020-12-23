const { GraphQLID, GraphQLString, GraphQLNonNull, GraphQLInt, GraphQLList } = require('graphql');

const VacancyType = require('../types/vacancy');
const Vacancy = require('../../model/Vacancy');
const TagType = require('../types/tag')

module.exports = {
	type: VacancyType,
	args: {
		title: {
			type: GraphQLString
		},
		function: {
			type: GraphQLString
		},
		description: {
			type: GraphQLString
		},
		salary: {
			type: GraphQLInt
		},
		companyId: {
			type: GraphQLID
		},
	},
	resolve: async (parent, args, { user }) => {
		if (user) {
			let vacancy = new Vacancy({
                title: args.title,
                function: args.function,
                description: args.description,
                salary: args.salary,
				companyId: args.companyId,
            })
            return vacancy.save()
		} else throw Error('you are not authorized')
	}
}

