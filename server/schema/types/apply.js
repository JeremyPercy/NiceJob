const UserType = require('./user')
const UserModel = require('../../model/User')

const {
    GraphQLObjectType,
    GraphQLID,
    GraphQLString,
    GraphQLNonNull
  } = require('graphql')

  const {
    GraphQLDate,
  } = require('graphql-iso-date');

  const ApplyType = new GraphQLObjectType({
    name: 'Apply',
    description: 'Users that apply',
    fields: () => ({
      id: {
        type: GraphQLID
      },
      date: {
        type: GraphQLDate
      },
      
    })
  })
  
module.exports = ApplyType
