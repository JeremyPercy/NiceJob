const UserModel = require('../../model/User')

const {
    GraphQLObjectType,
    GraphQLID,
    GraphQLString,
    GraphQLNonNull
  } = require('graphql')

  const UserDetailsType = new GraphQLObjectType({
    name: 'UserDetails',
    description: 'Users application/resume. Which a user can create and add information.',
    fields: () => ({
      id: {
        type: GraphQLID
      },
      name: {
        type: GraphQLString
      },
      lastName: {
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
  
module.exports = UserDetailsType