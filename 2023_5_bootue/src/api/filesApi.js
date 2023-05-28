import request from '@/utils/request'

export function filesPage (data) {
  return request({
    url: '/files/page',
    method: 'get',
    params: data
  })
}

export function filesDel (data) {
  return request({
    url: '/files/' + data.id,
    method: 'delete'
  })
}

export function fileUpload (data) {
  return request({
    url: '/files/update',
    method: 'post',
    data
  })
}

export function fileCompleteDel (data) {
  return request({
    url: '/files/complete/del',
    method: 'post',
    data
  })
}
