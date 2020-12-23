const { GraphQLSchema, GraphQLObjectType } = require('graphql');

// Queries
const getUser = require('./queries/getUser');
const getUserDetails = require('./queries/getUserDetails')
const getAllVacancies = require('./queries/getAllVacancies')
const getVacancy = require('./queries/getVacancy')
const searchQueryVacancy = require('./queries/searchQueryVacancies')
const getAllUserApplications = require('./queries/getAllUserApplications')

// Mutations
const register = require('./mutations/register')
const login = require('./mutations/login')
const addUserDetails = require('./mutations/addUserDetails')
const addVacancy = require('./mutations/addVacancy')
const addCompany = require('./mutations/addCompany')
const addApplication = require('./mutations/addApplication')
const updateUserDetails = require('./mutations/updateUserDetails')
const addExperience = require('./mutations/addExperience')
const addEducation = require('./mutations/addEducation')

const RootQuery = new GraphQLObjectType({
	name: 'RootQuery',
	fields: () => ({
		getUser: getUser,
		getUserDetails: getUserDetails,
		getAllVacancies: getAllVacancies,
		searchQueryVacancy: searchQueryVacancy,
		getAllUserApplications: getAllUserApplications,
		getVacancy: getVacancy,
	})
});

const Mutation = new GraphQLObjectType({
	name: 'Mutation',
	fields: () => ({
    register: register,
		login: login,
		addUserDetails: addUserDetails,
		addVacancy: addVacancy,
		addCompany: addCompany,
		addApplication: addApplication,
		updateUserDetails: updateUserDetails,
		addExperience: addExperience,
		addEducation: addEducation,
    })
});

module.exports = new GraphQLSchema({
	query: RootQuery,
	mutation: Mutation
});
