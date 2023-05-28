import request from '@/utils/request'

export function userSave (data) {
  return request({
    url: '/user',
    method: 'post',
    data: data
  })
}

export function userUpdate (data) {
  return request({
    url: '/user',
    method: 'put',
    data: data
  })
}

export function userDelete (data) {
  return request({
    url: '/user/' + data.id,
    method: 'delete'
  })
}

export function userPage (data) {
  return request({
    url: '/user/page',
    method: 'get',
    params: data
  })
}

export function userList () {
  return request({
    url: '/user',
    method: 'get'
  })
}

export function userDeles (data) {
  return request({
    url: '/user/del/batch',
    method: 'post',
    data: data.ids
  })
}

export function userLogin (data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function userInfo (data) {
  return request({
    url: '/user/username/' + data.username,
    method: 'get'
  })
}

export default {
  userSave,
  userDelete,
  userList,
  userPage
}
