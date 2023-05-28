import request from '@/utils/request'

export function artivleSaveOrUpdate (data) {
  return request({
    url: '/article',
    method: 'post',
    data
  })
}

export function artivleDel (data) {
  return request({
    url: '/article/' + data.id,
    method: 'delete'
  })
}

export function artivleDels (data) {
  return request({
    url: '/article/del/batch',
    method: 'post',
    data
  })
}

export function artivleGetOne (data) {
  return request({
    url: '/article/' + data.id,
    method: 'get',
    data
  })
}

export function artivlePage (data) {
  return request({
    url: '/article/page',
    method: 'get',
    params: data
  })
}
