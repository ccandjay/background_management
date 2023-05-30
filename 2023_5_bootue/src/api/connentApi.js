import request from '@/utils/request'

export function getCommentById (data) {
  return request({
    url: '/comment/tree/' + data.id,
    method: 'get'
  })
}

export function commentSave (data) {
  return request({
    url: '/comment',
    method: 'post',
    data
  })
}

export function delComment (data) {
  return request({
    url: '/comment/' + data.id,
    method: 'delete'
  })
}
