import request from '@/utils/request'

export function roleSave (data) {
  return request({
    url: '/role',
    method: 'post',
    data
  })
}

export function roleUpdate (data) {
  return request({
    url: '/role',
    method: 'put',
    data: data
  })
}

export function roleDelete (data) {
  return request({
    url: '/role/' + data.id,
    method: 'delete'
  })
}

export function rolePage (data) {
  return request({
    url: '/role/page',
    method: 'get',
    params: data
  })
}

export function roleList () {
  return request({
    url: '/role',
    method: 'get'
  })
}

export function roleDeles (data) {
  return request({
    url: '/role/del/batch',
    method: 'post',
    data: data.ids
  })
}

export function roleLogin (data) {
  return request({
    url: '/role/login/',
    method: 'post',
    data
  })
}

export function roleMenu (data) {
  return request({
    url: '/role/roleMenu',
    method: 'post',
    params: data
  })
}

export function roleMenuId (data) {
  return request({
    url: '/role/roleMenu/',
    method: 'get',
    params: data
  })
}

export default {
  roleSave,
  roleDelete,
  roleList,
  rolePage
}
