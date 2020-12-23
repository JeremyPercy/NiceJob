const { GraphQLObjectType, GraphQLID, GraphQLString, GraphQLNonNull, GraphQLList } = require('graphql');

// Types
const UserDetailsType = require('./userDetails')
const ExperienceType = require('./experience')
const EducationType = require('./education')
const ApplicationType = require('./application')

// Models
const UserDetailsModel = require('../../model/UserDetails');
const Experience = require('../../model/Experience');
const Education = require('../../model/Education');
const ApplicationModel = require('../../model/Application');

const UserType = new GraphQLObjectType({
	name: 'User',
	description: 'User type in graphql, which connects to user in the database.',
	fields: () => ({
		id: {
			type: GraphQLID
		},
		username: {
			type: new GraphQLNonNull(GraphQLString)
		},
		email: {
			type: new GraphQLNonNull(GraphQLString)
		},
		createdat: {
			type: GraphQLString
		},
		userDetails: {
			type: new GraphQLList(UserDetailsType),
			resolve(parent, args) {
				return UserDetailsModel.find({
					userId: parent.id
				});
			}
		},
		experience: {
			type: new GraphQLList(ExperienceType),
			resolve(parent, args) {
				return Experience.find({
					userId: parent.id
				});
			}
		},
		education: {
			type: new GraphQLList(EducationType),
			resolve(parent, args) {
				return Education.find({
					userId: parent.id
				});
			}
		},
		application: {
			type: new GraphQLList(ApplicationType),
			resolve(parent, args) {
				return ApplicationModel.find({
					userId: parent.id
				});
			}
		}
	})
});

module.exports = UserType;
