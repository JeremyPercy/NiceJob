const UserType = require('./user')
const UserModel = require('../../model/User')

const {
    GraphQLObjectType,
    GraphQLID,
    GraphQLString,
    GraphQLNonNull
  } = require('graphql')

  const ExperienceType = new GraphQLObjectType({
    name: 'Experience',
    description: 'Where users can save there experiences',
    fields: () => ({
      id: {
        type: GraphQLID
      },
      companyName: {
        type: GraphQLString
      },
      experience: {
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
  
module.exports = ExperienceType