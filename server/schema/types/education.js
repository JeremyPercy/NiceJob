const UserType = require('./user')
const User = require('../../model/User')

const {
    GraphQLObjectType,
    GraphQLID,
    GraphQLString,
    GraphQLNonNull
  } = require('graphql')

  const EducationType = new GraphQLObjectType({
    name: 'Education',
    description: 'Find the education of users.',
    fields: () => ({
      id: {
        type: GraphQLID
      },
      diploma: {
        type: GraphQLString
      },
      school: {
        type: GraphQLString
      },
      periodeFrom: {
        type: GraphQLString
      },
      periodeTo: {
        type: GraphQLString
      },
      
    })
  })
  
module.exports = EducationType