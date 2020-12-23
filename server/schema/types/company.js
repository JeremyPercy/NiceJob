const UserModel = require('../../model/User')

const {
    GraphQLObjectType,
    GraphQLID,
    GraphQLString,
    GraphQLNonNull
  } = require('graphql')

  const CompanyType = new GraphQLObjectType({
    name: 'Company',
    description: 'Company type where data is defined',
    fields: () => ({
      id: {
        type: GraphQLID
      },
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
      phoneNumber: {
        type: GraphQLString
      },
      user: {
        type: require('./user'),
        resolve(parent, args) {
          return UserModel.findById( 
            parent.userId
          )
        }
      }
    })
  })
  
module.exports = CompanyType