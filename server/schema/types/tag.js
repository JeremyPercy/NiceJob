const Tag = require('../../model/Tag');

const { GraphQLObjectType, GraphQLID, GraphQLString, GraphQLNonNull } = require('graphql');

const TagType = new GraphQLObjectType({
	name: 'Tag',
	description: 'Apply tags to vacancies',
	fields: () => ({
		id: {
			type: GraphQLID
		},
		title: {
			type: GraphQLString
		}
	})
});

module.exports = TagType;
