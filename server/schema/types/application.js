const UserModel = require('../../model/User')
const VacancyType = require('../types/vacancy')
const VacancyModel = require('../../model/Vacancy')

const {
    GraphQLObjectType,
    GraphQLID,
    GraphQLString,
    GraphQLNonNull
  } = require('graphql')

  const {
    GraphQLDate,
  } = require('graphql-iso-date');

  const ApplicationType = new GraphQLObjectType({
    name: 'Application',
    description: 'Users that apply',
    fields: () => ({
      id: {
        type: GraphQLID
      },
      date: {
        type: GraphQLString
      },
      user: {
        type: require('./user'),
        resolve(parent, args) {
          return UserModel.findById( 
            parent.userId
          )
        }
      },
      vacancy: {
        type: VacancyType,
        resolve(parent, args) {
          return VacancyModel.findById( 
            parent.vacancyId
          )
        }
      }
      
    })
  })
  
module.exports = ApplicationType
