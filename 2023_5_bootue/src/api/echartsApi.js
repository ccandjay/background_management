import request from '@/utils/request'

export function echartsMembers (data) {
  return request({
    url: '/echarts/members',
    method: 'get'

  })
}

export function fileFrontAll () {
  return request({
    url: '/echarts/file/front/all',
    method: 'get'
  })
}
